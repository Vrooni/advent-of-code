<div class="year-container">
  <button class="year-button closed" onclick="toggle(this)">
    <?php echo $year; ?>
    <img src="resources/chevron-up.svg" alt="chevron up icon">
  </button>
  <div class="days-container hidden">
    <div class="days-inner-container">
      <?php for ($day = 1; $day <= $days; $day++) {
        require("days-container.php");
      }
      ?>
    </div>
  </div>
</div>