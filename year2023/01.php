<?php
// Part one
$input = file("files/01.txt", FILE_IGNORE_NEW_LINES);
$result = 0;

foreach ($input as $line) {
  $line_chars = str_split($line);
  $numbers = [];

  foreach ($line_chars as $char) {
    if (is_numeric($char)) {
      $numbers[] = $char;
    }
  }

  $number = $numbers[0] . end($numbers);
  $result += $number;
}

echo $result . "<br>";


// Part two
$result = 0;
$numbers_map = [
  "one" => 1,
  "two" => 2,
  "three" => 3,
  "four" => 4,
  "five" => 5,
  "six" => 6,
  "seven" => 7,
  "eight" => 8,
  "nine" => 9
];

foreach ($input as $line) {
  $numbers = [];

  $line_length = strlen($line);
  for ($i = 0; $i < $line_length; $i++) {
    $number = $line[$i];

    if (is_numeric($number)) {
      $numbers[] = $number;
    }

    foreach ($numbers_map as $number_string => $number_int) {
      $word_length = strlen($number_string);
      $end_index = $i + $word_length;

      if (
        $end_index <= $line_length &&
        substr($line, $i, $word_length) === $number_string
      ) {
        $numbers[] = $number_int;
      }
    }
  }

  $number = $numbers[0] . end($numbers);
  $result += $number;
}

echo $result . "<br>";
