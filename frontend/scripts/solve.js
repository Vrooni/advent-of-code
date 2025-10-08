function solve(year, day) {
  const lang = document.getElementById("code-lang").textContent.trim();
  const solution1 = document.getElementById("solution-1");
  const solution2 = document.getElementById("solution-2");

  if (document.getElementById("input").value.length === 0) {
    showError(solution1, solution1.parentElement);
    showError(solution2, solution2.parentElement);
    return;
  }

  // loading
  solution1.innerHTML = ""
  solution2.innerHTML = ""
  solution1.parentElement.classList.remove("success");
  solution1.parentElement.classList.remove("error");
  solution2.parentElement.classList.remove("success");
  solution2.parentElement.classList.remove("error");
  showLoading();

  if (lang === "PHP") {
    const input = document.getElementById("input").value
      .split("\n")
      .map(line => line.trim());

    while (input[input.length - 1].trim() === "") {
      input.pop();
    }

    solvePHP(year, day, "1", input, solution1);
    solvePHP(year, day, "2", input, solution2);
  }

  if (lang === "Java") {
    const input = document.getElementById("input").value;

    solveJava(year, day, "1", input, solution1);
    solveJava(year, day, "2", input, solution2);
  }
}

function solvePHP(year, day, part, input, element) {
  fetch("backend/solutions/php-solutions/year" + year + "/Day" + day + "_" + part + ".php", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: "input=" + encodeURIComponent(JSON.stringify(input))
  })
  .then(response => {
    hideLoading(part);
    return response.text();
  })
  .then(data => {
    if (data.includes("error")) {
      showError(element, element.parentElement);
    } else {
      element.innerHTML = data;
      element.parentElement.classList.add("success");
      element.parentElement.classList.remove("error");
    }
  })
  .catch(error => {
    hideLoading(part);
    showError(element, element.parentElement);
  });
}

function solveJava(year, day, part, input, element) {
  fetch("http://localhost:8080/solve?year=" + year + "&day=" + day + "&part=" + part, {
    method: "POST",
    headers: { 'Content-Type': 'text/plain' },
    body: input
  })
  .then(response => {
    hideLoading(part);
    return response.text();
  })
  .then(data => {
    if (data.includes("Invalid")) {
      showError(element, element.parentElement);
    } else {
      element.innerHTML = data;
      element.parentElement.classList.add("success");
      element.parentElement.classList.remove("error");
    }
  })
  .catch(error => {
    hideLoading(part);
    showError(element, element.parentElement);
  });
}

function showError(solution, parent) {
  parent.classList.add("error");
  parent.classList.remove("success");
  solution.innerHTML = "Invalid input";
}

function showLoading() {
  document.querySelectorAll(".loading").forEach(element => {
    element.classList.remove("hide");
  });
}

function hideLoading(part) {
  document.querySelectorAll(".loading")[part-1].classList.add("hide");
}