<template>
    <div class="welcome-container">
        <div class="foto-container">
            <img src="@/assets/raspberry-l.png" alt="raspberry" class="picture">
        </div>
        <div class="text-container">
            <h1 class="question-header">Czym jest tak naprawdę nasz produkt?</h1>
            <p>Nasz produkt jest gadżetem dla fanów kolei, entuzjastów komunikacji miejskiej i wszystkich tych, którzy chcą być na bieżąco z najbliższymi odjazdami z wybranej stacji PKP lub przystanku.</p>
            <p>Podstawę całego przedsięwzięcia stanowi stworzona przez nas aplikacja webowa, która w połączeniu z autorskim serwerem, bazą danych i odpowiednio skonfigurowanym mikrokomputerem Raspberry Pi oferuje ogromny potencjał do dalszego rozwoju tego produktu. </p>
        </div>
    </div>
    <div class="files-container">
        <h1 class="pkg-text">Jeżeli chcesz sprawdzić jak aplikacja działałaby na twoim urządzeniu, poniżej możesz pobrać pakiety instalacyjne</h1>
        <div class="ports">
            <a class="pkg-1">
                <img class="port" src="@/assets/icons8-windows-50.png" alt="windows">
                <p>m_tip_device.exe</p>
            </a>
            <a class="pkg-1">
                <img class="port" src="@/assets/icons8-ubuntu-50.png" alt="ubuntu">
                <p>m_tip_device.deb</p>
            </a>
            <a class="pkg-1">
                <img class="port" src="@/assets/icons8-raspberry-pi-50.png" alt="raspbian">
                <p>m_tip_device.ARMv7.deb</p>
            </a>
        </div>
        <h1 class="pkg-text">Po więcej informacji dotyczących konfiguracji zapraszamy do dokumentacji projektu</h1>
    </div>
    <Footer/>
</template>
<script>
import Footer from '@/components/Footer.vue';
export default {
    name: 'WelcomePage',

    components: {
        Footer,
    }
}
</script>
<style>

.welcome-container {
    width: 80%;
    margin: 40px auto;
    display: flex;
    color: var(--appblue);
    font-family: 'Teko', sans-serif;
    font-size: 22px;
}


.foto-container {
    width: 50%;
    padding-right: 5%;
    display: flex;
    align-items: center;
}

.text-container {
    width: 40%;
    background: linear-gradient(var(--navMenuColor), var(--themeMenu));
    border-radius: 20px;
    margin: 20px auto;
    padding: 20px;
    display: flex;
    flex-direction: column;
}

.files-container {
    width: 80%;    
    background: linear-gradient(var(--navMenuColor), var(--themeMenu));
    margin: 40px auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    color: var(--appblue);
    font-family: 'Teko', sans-serif;
    border-radius: 20px;
}

.question-header {
    text-align: start;
    margin: 0 0 20px 0;
}

.pkg-text {
    text-align: center;
    margin: 30px auto 80px auto;
}

.pkg-1 {
    width: 140px;
    height: 180px;
    background-color: #ff7300;
    display: flex;
    flex-direction: column;
    border-radius: 20px;
    margin-bottom: 40px;
    cursor: pointer;
}

.pkg-1:hover {
    background-color: white;
}

.pkg-1 p {
    margin: auto;
    color: black;
}

.text-container p  {
    text-align: start;
}

.ports {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

.port {
    width: 80px;
    height: 80px;
    margin: auto;
}

.picture {
    width: 100%;
}


@media screen and (max-width: 1000px){
    .welcome-container {
    display: grid;
}


.foto-container {
    width: 100%;
}

.text-container {
    width: 100%;
}

}

</style>