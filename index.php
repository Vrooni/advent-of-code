<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="resources/icon.png" type=" image/png">
  <link rel="stylesheet" href="index.css">
  <script src="script.js" defer></script>
  <title>Advent of Code Solutions</title>
</head>

<body>
  <main>
    <h1>
      Advent of Code Solutions
    </h1>

    <section class="years-section">
      <?php
      require_once("year.php");

      $time_zone = new DateTimeZone('Europe/Berlin');
      $date = new DateTime('now', $time_zone);

      $current_year = (int)$date->format("Y");
      $current_month = (int)$date->format("m");
      $current_day = (int)$date->format("d");
      $current_hour = (int)$date->format("h");

      $to_year = $current_month === 12 ? $current_year : $current_year - 1;
      $available_day = $current_hour >= 6 ? $current_day : $current_day - 1;

      for ($year = 2015; $year <= $to_year; $year++) {
        $days = $year < $current_year ? 25 : $available_day;
        echo get_year_container($year, $days);
      }
      ?>
    </section>
  </main>
</body>

</html>