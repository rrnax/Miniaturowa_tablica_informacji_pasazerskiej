<template>
    <div class="device-view">
        <div class="info-show">
            <h1 class="section-title">Obserwowane przystanki</h1>
            <button class="show-btn" @click="showDisplay">Podgląd Urządzenia</button>
        </div>
        <hr/>
        <SubscribedStopsList/>
        <div class="info-show">
            <h1 class="section-title">Motywy i style urządzenia</h1>
            <button class="show-btn" @click="showDisplay">Podgląd Urządzenia</button>
        </div>
        <hr/>
        <DeviceStyles/>
    </div>
  </template>
  
  <script>
  import SubscribedStopsList from '@/components/deviceComponents/SubscribedStopsList.vue';
  import DeviceStyles from '@/components/deviceComponents/DeviceStyles.vue';
  export default {
    name: "DeviceView",

    components: {
        SubscribedStopsList,
        DeviceStyles,
    },

    methods: {
        showDisplay(){
            this.$router.push('/display');
        }
    }
 
  }
</script>

<style>
.device-view {
    width: 60%;
    margin: auto;
    display: grid;
    font-size: 22px;
    font-family: 'Teko', sans-serif;
    color: var(--appblue);
}

.info-show {
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
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  color: var(--whiteText);
  border: 1px solid var(--appblue);
  border-radius: 20px;
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
    .info-show {
    width: 100%;
    margin: 0;
    display: grid;
    }
}

</style>