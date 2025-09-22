<?php
$classes_prev = "prev";
$classes_next = "next";
$disabled_prev = "";
$disabled_next = "";

if (isset($_POST["day-and-year"])) {
  $day_year = explode(":", $_POST["day-and-year"]);
  $selected_day = $day_year[0];
  $selected_year = $day_year[1];

  validate_input();

  $prev_day = $selected_day - 1;
  $next_day = $selected_day + 1;
  $prev_year = $selected_year;
  $next_year = $selected_year;

  if ($next_day >= 26) {
    $next_day = 1;
    $next_year++;
  }

  if ($prev_day <= 0) {
    $prev_day = 25;
    $prev_year--;
  }

  if ($selected_year <= 2015 && $selected_day <= 1) {
    $classes_prev = "disabled";
    $disabled_prev = "disabled";
  }

  $time_zone = new DateTimeZone('Europe/Berlin');
  $date = new DateTime('now', $time_zone);
  $next_date = new DateTime("$next_year-12-$next_day 06:00:00", $time_zone);

  if ($next_date > $date) {
    $classes_next = "disabled";
    $disabled_next = "disabled";
  }
}

function validate_input()
{
  global $selected_year;
  global $selected_day;

  $time_zone = new DateTimeZone('Europe/Berlin');
  $date = new DateTime('now', $time_zone);

  $current_year = (int)$date->format("Y");
  $current_month = (int)$date->format("m");
  $current_day = (int)$date->format("d");
  $current_hour = (int)$date->format("G");

  if (
    $selected_day < 1 ||
    $selected_day > 25 ||
    $selected_year < 2015 ||
    new DateTime("$selected_year-12-$selected_day 06:00:00", $time_zone) > $date
  ) {
    $selected_year = $current_month === 12 ? $current_year : $current_year - 1;
    $selected_day = $current_hour >= 6 ? $current_day : $current_day - 1;
    $selected_day = $selected_year < $current_year ? 25 : $selected_day;
  }
}

function get_solution($year, $day)
{
  $path_to_solutions = "../../backend/solutions";

  return
    file_exists("$path_to_solutions/java-solutions/year$year/Day$day.java") ||
    file_exists("$path_to_solutions/solutions/java-solutions/year$year$day/Day" . $day . "_1.java") ||
    file_exists("$path_to_solutions/solutions/java-solutions/year$year/Day" . $day . "_2.java") ||
    file_exists("$path_to_solutions/solutions/php-solutions/year$year/Day$year.php");
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <link rel="shortcut icon" href="../styling/icon.png" type=" image/png">
  <link rel="stylesheet" href="../styling/variables.css">
  <link rel="stylesheet" href="../styling/solution.css">
  <link rel="stylesheet" href="../styling/general.css">
  <link rel="stylesheet" href="../styling/prism.css">
  <title> <?php echo "AOC $selected_year - Day $selected_day"; ?></title>
  <script src="../scripts/prism.js" defer></script>
</head>

<body>
  <header>
    <nav>
      <a class="green" href="/advent-of-code">
        Home
      </a>

      <i class="fa-solid fa-chevron-right"></i>
      <a class="red" href=<?php echo "/advent-of-code?year=$selected_year" ?>>
        <?php echo $selected_year; ?>
      </a>

      <i class="fa-solid fa-chevron-right"></i>
      <a class="red">
        <span class="blue">Day</span>
        <?php echo " $selected_day"; ?>
      </a>
    </nav>

    <section class="title-section">
      <form action="solution.php" method="post">
        <button
          class="<?php echo $classes_prev ?> btn secondary"
          type="submit"
          name="day-and-year"
          value="<?php echo "$prev_day:$prev_year"; ?>"
          <?php echo $disabled_prev ?>>
          <i class="fa-solid fa-chevron-left"></i>
          Prev
        </button>
      </form>

      <h1>
        <span class="red"><?php echo $selected_year; ?></span>
        <span class="green"> - </span>
        <span class="blue">Day </span>
        <span class="red"><?php echo $selected_day; ?></span>
        <span class="green">Solution</span>
      </h1>

      <form action="solution.php" method="post">
        <button
          class="<?php echo $classes_next ?> btn secondary"
          type="submit"
          name="day-and-year"
          value="<?php echo "$next_day:$next_year"; ?>"
          <?php echo $disabled_next ?>>
          Next
          <i class="fa-solid fa-chevron-right"></i>
        </button>
      </form>
    </section>
  </header>

  <main>
    <?php
    if (get_solution($selected_year, $selected_day)) {
      require_once("../components/solution-cards-container.php");
    } else {
      require_once("../components/solution-sad-reindeer.php");
    }
    ?>
  </main>
</body>

</html>