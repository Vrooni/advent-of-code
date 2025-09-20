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

?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <link rel="shortcut icon" href="resources/icon.png" type=" image/png">
  <link rel="stylesheet" href="variables.css">
  <link rel="stylesheet" href="solution.css">
  <link rel="stylesheet" href="general.css">
  <title> <?php echo "AOC $selected_year - Day $selected_day"; ?></title>
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
    <div class="cards-container">
      <div class="card-container">
        <form action="" method="post">
          <p>Enter your input:</p>
          <textarea class="textarea" name="" id=""></textarea>

          <div class="buttons">
            <a class="btn secondary" href="<?php echo "https://adventofcode.com/$selected_year/day/$selected_day" ?>" target="_blank" rel="noopener noreferrer">
              Problem
            </a>
            <button class="btn primary" type="submit">Solve</button>
          </div>
        </form>
      </div>

      <div class="output-container">
        <div class="card-container output">
          <p class="part-title">Part 1:</p>
          <p>Safljwelgkwjel</p>
        </div>

        <div class="card-container output">
          <p class="part-title">Part 2:</p>
          <p>Safljwelgkwjel</p>
        </div>
      </div>

      <div class="card-container solution">
        <h2>Solution</h2>
        <textarea class="textarea">
        </textarea>
      </div>
  </main>
</body>

</html>