<?php

namespace Day2_1;

const MAX_RED = 12;
const MAX_GREEN = 13;
const MAX_BLUE = 14;

$result = 0;

foreach ($input as $game) {
  $game = str_replace(" ", "", $game);
  $game = explode(":", $game);

  $id = (int) str_replace("Game", "", $game[0]);
  $subsets = explode(";", $game[1]);

  $isPossible = true;

  foreach ($subsets as $subset) {
    $cubes = explode(",", $subset);

    foreach ($cubes as $cube) {
      if (str_ends_with($cube, "red")) {
        if ((int) str_replace("red", "", $cube) > MAX_RED) {
          $isPossible = false;
          break;
        }
      }

      if (str_ends_with($cube, "green")) {
        if ((int) str_replace("green", "", $cube) > MAX_GREEN) {
          $isPossible = false;
          break;
        }
      }

      if (str_ends_with($cube, "blue")) {
        if ((int) str_replace("blue", "", $cube) > MAX_BLUE) {
          $isPossible = false;
          break;
        }
      }
    }
  }

  if ($isPossible) {
    $result += $id;
  }
}

return $result;
