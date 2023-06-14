<template>
    <footer>
        <div>
            <a href="https://github.com/rrnax/Miniaturowa_tablica_informacji_pasazerskiej">
                <img class='icon' src="@/assets/github-icon.png"/>
            </a>
            <a href="https://rrnax.github.io/Miniaturowa_tablica_informacji_pasazerskiej/">
                <img class='icon' src="@/assets/world-globe-line-icon.png"/>
            </a>
            <a href="https://dokumentacja-projektu-zespolowego.vercel.app">
                <img class='icon' src="@/assets/copied-icon.png" alt="world">
            </a>
        </div>
        <p class="infos">@Zespół nr 2 w Programowanie Zespołowe UMK 2023</p>
    </footer>
</template>

<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Footer',
}

</script>

<style>

.icon {
    width: 40px;
    height: 40px;
    margin: 0 20px;
    display: inline-block;
}

.infos {
    margin-bottom: 10px;
}

footer {
  width: 100%;
  height: 120px;
  background: linear-gradient( var(--themeMenu), var(--navMenuColor));
  display: grid;
  justify-content: center;
  font-family: 'Teko', sans-serif;
  color: gray;
  font-size: 14px;
  align-items: end;
  row-gap: 0;
}

</style>