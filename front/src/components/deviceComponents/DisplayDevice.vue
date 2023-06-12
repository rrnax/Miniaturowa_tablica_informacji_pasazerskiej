<template>
    <div v-if='this.userStore.getIsActive' class="display-continer">
        <button @click="deviceSlide" class="roller">
            <p id="showing">Pdogląd</p>
            <img id="arrow" src="../../assets/angle-right-icon.png">
        </button>
        <DisplayView/>
        <!-- <div v-if="this.apiStore.getLoadedInfo" class="loader" id="visable"></div> -->
    </div>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';
import { useUserStore } from '@/store/user.stroe';
import DisplayView from '@/views/DisplayView.vue';

export default {
    name: 'DisplayDevice',

    setup(){
        const apiStore = useApiStore();
        const userStore = useUserStore();
        return { apiStore, userStore };
    },

    props: ['isActive'],

    components: {
        DisplayView,
    },

    methods: {
        deviceSlide(){
            let devicePanel = document.querySelector(".display-continer");
            let rollerBtn = document.querySelector(".roller");
            let device = document.querySelector(".display-view");
            let arrow = document.querySelector("#arrow");
            devicePanel.classList.toggle("display-open");
            rollerBtn.classList.toggle("roller-open");
            device.classList.toggle("open");
            arrow.classList.toggle("rote");
        },
    }
}
</script>

<style>


.display-continer {
    margin: 0;
    padding: 0;
    width: 0;
    height: 150vh;
    bottom: 0;
    position: sticky;
    z-index: 1;
    background: var(--showpanel);
    overflow-x: visible;
    transition: all 2s ease;
}

.roller {
    width: 150px;
    height: 60px;
    margin: auto;
    display: flex;
    justify-content: center;
    align-items: center;
    position: sticky;
    top: 100px;
    left: 0;
    background: var(--appblue);
    border: none;
    font-family: 'PT Sans', sans-serif;
    font-size: 22px;
    color: var(--whiteText);
    border-radius: 0 20px 20px 0;
    cursor: pointer;
    transition: all 2s ease;

}

.display-open {
    width: 520px;
}

.roller-open {
    background: none;
    left: 520px;
}

.open {
    width: 480px;
}

#showing {
    display: inline-block;
    margin-right: 20px;
}

#arrow {
    display: inline-block;
    width: 30px;
    height: 40px;
    transition: all 2s ease;
}

.rote {
    transform: rotate(180deg);
}

</style>