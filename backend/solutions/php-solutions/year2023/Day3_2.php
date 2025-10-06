<?php

namespace Day3_2;

class Number
{
  public $value;
  public $x;
  public $y;

  function __construct($value, $x, $y)
  {
    $this->value = $value;
    $this->x = $x;
    $this->y = $y;
  }
}

class Position
{
  public $x;
  public $y;

  function __construct($x, $y)
  {
    $this->x = $x;
    $this->y = $y;
  }
}


function getNumbers($input)
{
  $numbers = [];

  for ($y = 0; $y < sizeof($input); $y++) {
    $line = $input[$y];
    $number = "";

    for ($x = 0; $x < strlen($line); $x++) {
      $char = $line[$x];

      if (is_numeric($char)) {
        $number .= $char;
      } else {
        if ($number) {
          $numbers[] = new Number($number, $x - 1, $y);
        }
        $number = "";
      }
    }

    if ($number) {
      $numbers[] = new Number($number, $x - 1, $y);
    }
  }

  return $numbers;
}

function get_numbers_by_index($numbers)
{
  $numbers_by_index = [];

  foreach ($numbers as $number) {
    $start_x = $number->x - strlen($number->value) + 1;

    for ($x = $start_x; $x <= $number->x; $x++) {
      $numbers_by_index["$x;$number->y"] = $number;
    }
  }

  return $numbers_by_index;
}

function get_potiential_gears($input)
{
  $potential_gears = [];

  for ($y = 0; $y < sizeof($input); $y++) {
    $line = $input[$y];

    for ($x = 0; $x < strlen($line); $x++) {
      if ($line[$x] === "*") {
        $potential_gears[] = new Position($x, $y);
      }
    }
  }

  return $potential_gears;
}

function get_gear_numbers($potential_gear, $input, $numbers)
{
  $start_x = max($potential_gear->x - 1, 0);
  $start_y = max($potential_gear->y - 1, 0);
  $end_x = min($potential_gear->x + 1, strlen($input[0]) - 1);
  $end_y = min($potential_gear->y + 1, sizeof($input) - 1);

  $gear_numbers = [];

  for ($x = $start_x; $x <= $end_x; $x++) {
    for ($y = $start_y; $y <= $end_y; $y++) {
      $number = $numbers["$x;$y"] ?? 0;

      if ($number && !in_array($number, $gear_numbers, true)) {
        $gear_numbers[] = $number;
      }
    }
  }

  return $gear_numbers;
}

$input =  json_decode($_POST["input"]);

$numbers = getNumbers($input);
$numbers = get_numbers_by_index($numbers);
$potential_gears = get_potiential_gears($input);

$gears_numbers = array_map(function ($number) use ($input, $numbers) {
  return get_gear_numbers($number, $input, $numbers);
}, $potential_gears);

$result = 0;

foreach ($gears_numbers as $gear_numbers) {
  if (sizeof($gear_numbers) === 2) {

    $gear_numbers = array_map(function ($number) {
      return (int) $number->value;
    }, $gear_numbers);

    $result += array_product($gear_numbers);
  }
}

echo $result;
