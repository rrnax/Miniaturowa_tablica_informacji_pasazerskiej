const changeThemeButton = document.querySelector("button#changeThemeButton");

/* Change theme function */
changeThemeButton.addEventListener("click",(e)=>{
    e.preventDefault();
    document.body.classList.toggle("lightMode");
});