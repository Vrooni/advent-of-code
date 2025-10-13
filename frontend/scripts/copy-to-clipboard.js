function copyToClipboard(element) {
  const info = element.querySelector(".info");
  const solution = element.querySelector(".part-solution")
  info.innerText = "Copied!";
  info.classList.add("green");
 
  if (navigator.clipboard && window.isSecureContext) {
    navigator.clipboard.writeText(solution.innerText);
  } 
  
  // workaround for http
  else {
    const textArea = document.createElement("textarea");
    textArea.value = solution.innerText;
    document.body.appendChild(textArea);
    textArea.focus({preventScroll: true});
    textArea.select();
    document.execCommand("copy");
    document.body.removeChild(textArea);
  }

  setTimeout(
    function() {
      info.innerText = "Copy";
      info.classList.remove("green");
  }, 3000);
}
