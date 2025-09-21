<?php
$solution_exists =
  file_exists("backend/solutions/java-solutions/year$year/Day$day.java") ||
  file_exists("backend/solutions/java-solutions/year$year/Da" . $day . "_1.java") ||
  file_exists("backend/solutions/java-solutions/year$year/Da" . $day . "_2.java") ||
  file_exists("backend/solutions/php-solutions/year$year/Day$day.php");
?>


<form action="frontend/pages/solution.php" method="post">
  <button class="day-button <?php echo $solution_exists ? "solution" : "" ?>" name="day-and-year" value="<?php echo "$day:$year" ?>" type="submit">
    <?php echo $day ?>
  </button>
</form>