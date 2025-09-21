<?php
const MAX_RED = 12;
const MAX_GREEN = 13;
const MAX_BLUE = 14;

//Part one
$lines = file("files/02.txt", FILE_IGNORE_NEW_LINES);
$result = 0;

foreach ($lines as $game) {
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

echo $result . "<br>";


//Part two
$result = 0;

foreach ($lines as $game) {
  $game = str_replace(" ", "", $game);
  $game = explode(":", $game);

  $subsets = explode(";", $game[1]);
  $reds = 0;
  $greens = 0;
  $blues = 0;

  foreach ($subsets as $subset) {
    $cubes = explode(",", $subset);

    foreach ($cubes as $cube) {
      if (str_ends_with($cube, "red")) {
        $red = (int) str_replace("red", "", $cube);
        $reds = max($red, $reds);
      }

      if (str_ends_with($cube, "green")) {
        $green = (int) str_replace("green", "", $cube);
        $greens = max($green, $greens);
      }

      if (str_ends_with($cube, "blue")) {
        $blue = (int) str_replace("blue", "", $cube);
        $blues = max($blue, $blues);
      }
    }
  }

  $result += $reds * $greens * $blues;
}

echo $result . "<br>";
