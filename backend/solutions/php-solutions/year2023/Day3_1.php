<?php

namespace Day3_1;

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

function is_surrounded_by_symbol($number, $input): bool
{
  $start_x = $number->x - strlen($number->value);
  $start_y = $number->y - 1;
  $end_x = $number->x + 1;
  $end_y = $number->y + 1;

  $start_x = max($start_x, 0);
  $start_y = max($start_y, 0);
  $end_x = min($end_x, strlen($input[0]) - 1);
  $end_y = min($end_y, sizeof($input) - 1);

  for ($y = $start_y; $y <= $end_y; $y++) {
    for ($x = $start_x; $x <= $end_x; $x++) {
      $char = $input[$y][$x];

      if (!is_numeric($char) && $char !== ".") {
        return true;
      }
    }
  }

  return false;
}

$input =  json_decode($_POST["input"]);
$numbers = getNumbers($input);

$part_numbers = array_filter($numbers, function ($number) use ($input) {
  return is_surrounded_by_symbol($number, $input);
});

$part_numbers = array_map(function ($number) {
  return $number->value;
}, $part_numbers);

echo array_sum($part_numbers);
