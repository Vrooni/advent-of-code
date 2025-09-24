<?php
$solution_exists =
  file_exists("backend/solutions/java-solutions/year$year/Day" . $day . "_1.class") &&
  file_exists("backend/solutions/java-solutions/year$year/Day" . $day . "_2.class") ||
  file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_1.php") &&
  file_exists("backend/solutions/php-solutions/year$year/Day" . $day . "_2.php");
?>


<form action="frontend/pages/solution.php" method="post">
  <button class="day-button <?php echo $solution_exists ? "solution" : "" ?>" name="day-and-year" value="<?php echo "$day:$year" ?>" type="submit">
    <?php echo $day ?>
  </button>
</form>