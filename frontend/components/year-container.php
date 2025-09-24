<?php
$closed = "closed";
$hidden = "hidden";

if ($selected_year == $year) {
  $closed = "";
  $hidden = "";
}
?>

<div class="year-container">
  <button class="year-button <?php echo $closed ?>" onclick="toggle(this)">
    <?php echo $year; ?>
    <i class="fa-solid fa-chevron-down"></i>
  </button>
  <div class="days-container <?php echo $hidden ?>">
    <div class="days-inner-container">
      <?php for ($day = 1; $day <= $days; $day++) {
        require("day-button.php");
      }
      ?>
    </div>
  </div>
</div>