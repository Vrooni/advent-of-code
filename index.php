<?php
if (isset($_GET["year"])) {
  $selected_year = $_GET["year"];
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
  <link rel="stylesheet" href="frontend/styling/index.css">
  <link rel="stylesheet" href="frontend/styling/general.css">
  <script src="frontend/scripts/script.js" defer></script>
  <title>Advent of Code Solutions</title>
</head>

<body>
  <header>
    <h1>
      <span class="green">Advent</span>
      <span class="red"> of </span>
      <span class="blue"> Code </span>
      <span class="green">Solutions</span>
    </h1>
  </header>

  <main>
    <section class="years-section">
      <?php

      $time_zone = new DateTimeZone('Europe/Berlin');
      $date = new DateTime('now', $time_zone);


      $current_year = (int)$date->format("Y");
      $current_month = (int)$date->format("m");
      $current_day = (int)$date->format("d");
      $current_hour = (int)$date->format("G");

      $to_year = $current_month === 12 ? $current_year : $current_year - 1;
      $available_day = $current_hour >= 6 ? $current_day : $current_day - 1;

      for ($year = 2015; $year <= $to_year; $year++) {
        $days = $year < $current_year ? 25 : $available_day;
        require("frontend/components/year-container.php");
      }
      ?>
    </section>
  </main>
</body>

</html>