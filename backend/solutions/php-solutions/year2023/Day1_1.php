<?php

namespace Day1_1;

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

return $result;
