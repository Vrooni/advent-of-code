<?php

namespace Day2_2;

const MAX_RED = 12;
const MAX_GREEN = 13;
const MAX_BLUE = 14;

$result = 0;
$input =  json_decode($_POST["input"]);

foreach ($input as $game) {
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

echo $result;
