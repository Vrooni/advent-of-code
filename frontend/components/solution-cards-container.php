<?php
$path_to_php_solution = "../../backend/solutions/php-solutions/year$selected_year/Day$selected_day.php";
$path_to_java_solution = "../../backend/solutions/java-solutions/year$selected_year/Day$selected_day.java";
?>

<div class="cards-container">
  <div class="card-container">
    <form action="" method="post">
      <p>Enter your input:</p>
      <textarea class="textarea" name="" id=""></textarea>

      <div class="buttons">
        <a class="btn secondary"
          href="<?php echo "https://adventofcode.com/$selected_year/day/$selected_day" ?>"
          target="_blank"
          rel="noopener noreferrer">
          Problem
        </a>
        <button class="btn primary" type="submit">Solve</button>
      </div>
    </form>
  </div>

  <div class="output-container">
    <div class="card-container output">
      <p class="part-title">Part 1:</p>
      <p>Safljwelgkwjel</p>
    </div>

    <div class="card-container output">
      <p class="part-title">Part 2:</p>
      <p>Safljwelgkwjel</p>
    </div>
  </div>

</div>

<div class="solution-container">
  <h2>Solution</h2>
  <?php
  if (file_exists($path_to_php_solution)) {
    require("../components/solution-php.php");
  }
  if (file_exists($path_to_java_solution)) {
    require("../components/solution-java.php");
  }
  ?>
</div>