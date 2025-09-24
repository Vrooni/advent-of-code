function copyToClipboard(element) {
  const info = element.querySelector(".info");
  const solution = element.querySelector(".part-solution")
  info.innerText = "Copied!";
  info.classList.add("green");
  navigator.clipboard.writeText(solution.innerText)

  setTimeout(
    function() {
      info.innerText = "Copy";
      info.classList.remove("green");
  }, 3000);
}
