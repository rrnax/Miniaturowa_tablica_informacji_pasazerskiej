<template>
    <div class="device-view">
        <div class="configuration-show">
            <h1 class="section-title">Obserwowane przystanki</h1>
        </div>
        <hr/>
        <SubscribedStopsList @change="checkStopList"/>
        <div class="configuration-show">
            <h1 class="section-title">Motywy i style urządzenia</h1>
        </div>
        <hr/>
        <DeviceStyles/>
    </div>
    <DisplayDevice :dark-mode="this.darkMode" :is-active="isActive"/>
    <Footer />
</template>
  
  <script>
  import SubscribedStopsList from '@/components/deviceComponents/SubscribedStopsList.vue';
  import DeviceStyles from '@/components/deviceComponents/DeviceStyles.vue';
  import DisplayDevice from '@/components/deviceComponents/DisplayDevice.vue';
  import { useApiStore } from '@/store/apiManagment.store';
  import Footer from '@/components/Footer.vue';
  import { useUserStore } from '@/store/user.stroe';

  export default {
    name: "DeviceView",
    
    setup() {
        const apiStore = useApiStore();
        const userStore = useUserStore();
        return { apiStore, userStore };
    },

    props: ["darkMode"],

    components: {
        SubscribedStopsList,
        DeviceStyles,
        DisplayDevice,
        Footer,
    },

    methods: {
        checkStopList() {
            this.isActive = this.userStore.getIsActive;
        },


    }
 
  }
</script>

<style>
.device-view {
    width: 60%;
    display: grid;
    font-size: 22px;
    position: absolute;
    top: 80px;
    left: 0;
    right: 0;
    margin: 0 auto;
    z-index: 0;
    font-family: 'Teko', sans-serif;
    color: var(--appblue);
}

.configuration-show{
    width: 100%;
    margin: 60px 0 0 0;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;

}

.section-title {
    display: inline-block;
}

.show-btn {
  width: 200px;
  height: 40px;
  padding: 0;
  margin: 0;
  display: inline-block;
  background: var(--appblue);
  border: 1px solid var(--appblue);
  border-radius: 20px;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  color: var(--whiteText);
  cursor: pointer;
}

.show-btn:hover {
  background: var(--whiteText);
  color: var(--appblue);
}


@media screen and (max-width: 1200px) {
    .device-view {
        width: 70%;
    }
}

@media screen and (max-width: 1000px) {
    .device-view {
        width: 80%;
    }
}

@media screen and (max-width: 800px) {
    .device-view {
        width: 90%;
    }
}

@media screen and (max-width: 550px) {
    .configuration-show {
    width: 100%;
    margin-top: 100px;
    margin: 0;
    display: grid;
    }
}

</style>