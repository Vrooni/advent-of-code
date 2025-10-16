<?php
$code_solutions = [];
if ($solution_php_exists) {
  $code_solutions[] = "PHP";
}

if ($solution_java_exists) {
  $code_solutions[] = "Java";
}

$code_solution_language = $code_solutions[0] ?? "";
?>

<div class="cards-container">
  <div class="card-container">
    <p>Enter your input:</p>
    <textarea id="input" class="textarea"></textarea>

    <div class="buttons">
      <a class="btn secondary"
        href="<?php echo "https://adventofcode.com/$selected_year/day/$selected_day" ?>"
        target="_blank"
        rel="noopener noreferrer">
        Problem
      </a>
      <button class="btn primary" onclick="solve(<?php echo $selected_year; ?>, <?php echo $selected_day; ?>)">Solve</button>
    </div>
  </div>

  <div class="output-container">
    <div onclick="copyToClipboard(this)" class="card-container output <?php echo $selected_day == 25 ? "full-width" : "" ?>">
      <p class="part-title">Part 1</p>
      <p id="solution-1" class="part-solution"><?php echo $selected_day == 25 ? "Answer will be shown here" : "Answers will"; ?></p>
      <span class="info">Copy</span>
      <div class="loading hide">
        <i class="fa-solid fa-sleigh sleigh"></i>
        <img class="reindeer one" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
        <img class="reindeer two" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
        <img class="reindeer three" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
      </div>
    </div>

    <?php if ($selected_day != 25): ?>
      <div onclick="copyToClipboard(this)" class="card-container output">
        <p class="part-title">Part 2</p>
        <p id="solution-2" class="part-solution">be shown here</p>
        <span class="info">Copy</span>
        <div class="loading hide">
          <i class="fa-solid fa-sleigh sleigh"></i>
          <img class="reindeer one" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
          <img class="reindeer two" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
          <img class="reindeer three" src="frontend/resources/fly_reindeer.svg" alt="flying reindeer">
        </div>
      </div>
    <?php endif; ?>
  </div>
</div>

<div class="solution-container">
  <h2>Code Solution</h2>
  <?php
  if (sizeof($code_solutions) > 1) {
  ?>
    <div class="lang-container">
      <button class="prev" onclick="toggleLanguage(); selectPart(<?php echo $selected_year; ?>, <?php echo $selected_day; ?>)">
        <i class="fa-solid fa-chevron-left"></i>
      </button>

      <h3 id="code-lang" class="red">
        <?php echo $code_solution_language; ?>
      </h3>

      <button class="next" onclick="toggleLanguage(); selectPart(<?php echo $selected_year; ?>, <?php echo $selected_day; ?>)">
        <i class=" fa-solid fa-chevron-right"></i>
      </button>
    </div>
  <?php
  } else {
  ?>
    <div class="lang-container">
      <h3 id="code-lang" class="red">
        <?php echo $code_solution_language; ?>
      </h3>
    </div>
  <?php
  }
  ?>

  <form class="tag-container">
    <img class="one-tag active" src="frontend/resources/tag-one.svg" alt="one tag" onclick="selectPartOne(this, <?php echo $selected_year; ?>, <?php echo $selected_day; ?> )">
    <img class="two-tag <?php echo $selected_day == 25 ? "disabled" : "" ?>" src="frontend/resources/tag-two.svg" alt="two tag" onclick="selectPartTwo(this, <?php echo $selected_year; ?>, <?php echo $selected_day; ?> )">
  </form>

  <pre id="code-container" class="textarea">
  </pre>
</div>