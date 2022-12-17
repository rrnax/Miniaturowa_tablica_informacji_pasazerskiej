// Toggle button in navabar
function toggleButton(){
    let minMenu = document.querySelector(".navamenu");
    if(minMenu.className === "navamenu"){
        minMenu.className += " responsive";
    } else {
        minMenu.className = "navamenu";
    }
}
