function solve(year, day) {
  const lang = document.getElementById("code-lang").textContent.trim();
  const solution1 = document.getElementById("solution-1");
  const solution2 = document.getElementById("solution-2");

  //TODO loading screen
  solution1.innerHTML = "Solve..."
  solution2.innerHTML = "Solve..."
  solution1.parentElement.classList.remove("success");
  solution2.parentElement.classList.remove("error");

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
    console.log(response);
    return response.text();
})
  .then(data => {
    if (data.includes("error")) {
      element.innerHTML = "Invalid input";
      element.parentElement.classList.add("error");
      element.parentElement.classList.remove("success");
    } else {
      element.innerHTML = data;
      element.parentElement.classList.add("success");
      element.parentElement.classList.remove("error");
    }
  })
  .catch(error => {
    // element.innerHTML = "Invalid input";
    //TODO
    element.innerHTML = error;
    element.parentElement.classList.add("error");
    element.parentElement.classList.remove("success");
  });
}

function solveJava(year, day, part, input, element) {
  fetch("http://localhost:8080/solve?year=" + year + "&day=" + day + "&part=" + part, {
    method: "POST",
    headers: { 'Content-Type': 'text/plain' },
    body: input
  })
  .then(response => {
    console.log(response);
    return response.text();
  })
  .then(data => {
    if (data.includes("error")) {
      element.innerHTML = "Invalid input";
      element.parentElement.classList.add("error");
      element.parentElement.classList.remove("success");
    } else {
      element.innerHTML = data;
      element.parentElement.classList.add("success");
      element.parentElement.classList.remove("error");
    }
  })
  .catch(error => {
    // element.innerHTML = "Invalid input";
    //TODO
    element.innerHTML = error;
    element.parentElement.classList.add("error");
    element.parentElement.classList.remove("success");
  });
}