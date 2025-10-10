<?php
require_once "frontend/utils/utils.php";

$classes_prev = "prev";
$classes_next = "next";
$disabled_prev = "";
$disabled_next = "";

if (isset($_POST["day-and-year"])) {
  $day_year = explode(":", $_POST["day-and-year"]);
  $selected_day = $day_year[0];
  $selected_year = $day_year[1];
}

validate_selected($selected_year, $selected_day);

$prev_year = $selected_year;
$next_year = $selected_year;
$prev_day = $selected_day - 1;
$next_day = $selected_day + 1;

if ($next_day >= 26) {
  $next_day = 1;
  $next_year++;
}

if ($prev_day <= 0) {
  $prev_day = 25;
  $prev_year--;
}

$time_zone = new DateTimeZone('Europe/Berlin');
$date = new DateTime('now', $time_zone);
$next_date = new DateTime("$next_year-12-$next_day 06:00:00", $time_zone);

if ($selected_year <= 2015 && $selected_day <= 1) {
  $classes_prev = "disabled";
  $disabled_prev = "disabled";
}

if ($next_date > $date) {
  $classes_next = "disabled";
  $disabled_next = "disabled";
}

solution_exists($solution_php_exists, $solution_java_exists, $selected_year, $selected_day);

function validate_selected(&$selected_year, &$selected_day)
{
  $time_zone = new DateTimeZone('Europe/Berlin');
  $date = new DateTime('now', $time_zone);

  if (
    !isset($selected_year) || !isset($selected_day) ||
    $selected_day < 1 ||
    $selected_day > 25 ||
    $selected_year < 2015 ||
    new DateTime("$selected_year-12-$selected_day 06:00:00", $time_zone) > $date
  ) {
    $current_year = (int)$date->format("Y");
    $current_month = (int)$date->format("m");
    $current_day = (int)$date->format("d");
    $current_hour = (int)$date->format("G");

    $selected_year = $current_month === 12 ? $current_year : $current_year - 1;
    $selected_day = $current_hour >= 6 ? $current_day : $current_day - 1;
    $selected_day = $selected_year < $current_year ? 25 : $selected_day;
  }
}

function solution_exists(&$solution_php_exists, &$solution_java_exists, $year, $day)
{
  $solution_php_exists = file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_1.php") &&
    file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_2.php");

  $solution_java_exists =
    Utils::java_solution_exists($year, $day, 1, "backend/solutions/java-solutions") &&
    Utils::java_solution_exists($year, $day, 2, "backend/solutions/java-solutions");
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <link rel="shortcut icon" href="frontend/resources/icon.png" type=" image/png">
  <link rel="stylesheet" href="frontend/styling/variables.css">
  <link rel="stylesheet" href="frontend/styling/solution-nav.css">
  <link rel="stylesheet" href="frontend/styling/solution-input.css">
  <link rel="stylesheet" href="frontend/styling/solution-code.css">
  <link rel="stylesheet" href="frontend/styling/general.css">
  <link rel="stylesheet" href="frontend/styling/prism.css">
  <title> <?php echo "AOC $selected_year - Day $selected_day"; ?></title>
  <script src="frontend/scripts/prism.js" defer></script>
  <script src="frontend/scripts/copy-to-clipboard.js" defer></script>
  <script src="frontend/scripts/part-selection.js" defer></script>
  <script src="frontend/scripts/lang-selection.js" defer></script>
  <script src="frontend/scripts/solve.js" defer></script>
  <script src="frontend/scripts/lights.js" defer></script>
</head>

<body>
  <?php
  require_once("frontend/components/lights.php");
  ?>

  <header>
    <nav>
      <a class="green clickable" href="/advent-of-code">
        Home
      </a>

      <i class="fa-solid fa-chevron-right"></i>
      <a id="year" class="red clickable" href=<?php echo "/advent-of-code?year=$selected_year" ?>>
        <?php echo $selected_year; ?>
      </a>

      <i class="fa-solid fa-chevron-right"></i>
      <p class="red">
        <span class="blue">Day</span>
        <span id="day"><?php echo " $selected_day"; ?></span>
      </p>
    </nav>

    <section class="title-section">
      <form class="prev-form" action="solution.php" method="post">
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

      <form class="next-form" action="solution.php" method="post">
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
    if ($solution_php_exists || $solution_java_exists) {
      require_once("frontend/components/solution-cards-container.php");
    } else {
      require_once("frontend/components/solution-sad-reindeer.php");
    }
    ?>
  </main>
</body>

</html>