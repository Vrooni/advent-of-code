const svg = document.querySelector(".lights");

addLights(svg, "stroke1");
addLights(svg, "stroke2");
addLights(svg, "stroke3");
addLights(svg, "stroke4");
addLights(svg, "stroke5");

function addLights(svg, id) {
  const stroke = document.getElementById(id);

  for (let pos = 0; pos < stroke.getTotalLength(); pos += 30) {

    const point = stroke.getPointAtLength(pos);
    const xOffset = (Math.random() - 0.5) * 20;
    const yOffset = (Math.random() - 0.5) * 30;

    const light = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
    light.setAttribute("class", "light");
    light.setAttribute("cx", point.x + xOffset);
    light.setAttribute("cy", point.y + yOffset);
    light.setAttribute("r", 2 + Math.random() * 2);
    
    // TODO don't use svgs!!!
    // only animate 10%
    if (Math.random() < 0.1) {
      // light.style.animation = "glimmer 1.5s infinite alternate";
      // light.style.animationDelay = (Math.random() * 2) + "s";
    }

    svg.appendChild(light);
  }
}