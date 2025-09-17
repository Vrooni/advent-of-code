<?php
function get_year_container(int $year, int $days): string
{
  return '
      <div class="year-container">
        <button class="year-button closed" onclick="toggle(this)">' . $year . '
          <img src="resources/chevron-up.svg" alt="chevron up icon">
        </button>
        <div class="days-container hidden">
          <div class="days-inner-container"> ' . get_days($days) . ' </div>
        </div>
      </div>';
}

function get_days($days)
{
  $day_buttons = "";
  for ($day = 1; $day <= $days; $day++) {
    $day_buttons .= '<button class="day-button">' . $day . '</button>';
  }

  return $day_buttons;
}
