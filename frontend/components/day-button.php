<?php

require_once "frontend/utils/utils.php";

$solution_exists =
  file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_1.php") &&
  file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_2.php") ||
  Utils::java_solution_exists($year, $day, 1) &&
  Utils::java_solution_exists($year, $day, 2);
?>


<form action="solution.php" method="post">
  <button class="day-button <?php echo $solution_exists ? "solution" : "" ?>" name="day-and-year" value="<?php echo "$day:$year" ?>" type="submit">
    <?php echo $day ?>
  </button>
</form>