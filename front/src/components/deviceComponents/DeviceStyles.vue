<template>
    <div class="device-style">
        <p class="order">Wybierz wyświetlany motyw interfejsu urządzenia</p>
        <form>
            <div class="input-set">
                <input class="check" type="checkbox" value="retro" @click="selectOnlyOneCheckBox" id="retro"/>
                <label class="check-label" for="retro">Retro</label>
                <div class="see retro"></div>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="basic" @click="selectOnlyOneCheckBox" id="basic"/>
                <label class="check-label" for="basic">Standardowy</label>
                <div class="see basic"></div>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="contrast" @click="selectOnlyOneCheckBox" id="contrast"/>
                <label class="check-label" for="contrast">Kontrast</label>
                <div class="see contrast"></div>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="dracula" @click="selectOnlyOneCheckBox" id="dracula"/>
                <label class="check-label" for="dracula">Dracula</label>
                <div class="see dracula"></div>
            </div>
        </form>
    </div>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';

export default{
    name: "DeviceStyles",

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },

    //When component is rendering we download device style
    mounted(){
        this.apiStore.downloadConfiguration();
        let checkBoxList = document.querySelectorAll(".check");
        checkBoxList.forEach(element => {
            if(element.value === this.apiStore.getDeviceStyle){
                element.checked = true;
            }
        });
    },

    methods: {

        //Check if only one checkBox is sited
        selectOnlyOneCheckBox(event){
            this.apiStore.setStyle(event.target.value);
            let checkBoxList = document.querySelectorAll(".check");
            checkBoxList.forEach(element => {element.checked = false;});
            event.target.checked = true;
        }
    }   
}

</script>

<style>
.device-style {
    width: 100%;
    margin: auto;
    display: grid;
    justify-content: center;
    
}

.order{
    margin-bottom: 30px;
}

.input-set {
    width: 100%;
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
}

.check {
    width: 25px;
    margin: 0;
    display: inline-block;
}

.check-label {
    display: inline-block;
}

.see {
    width: 60px;
    height: 32px;
    margin: 0;
    padding: 0;
    display: inline-block;
    border: 2px solid var(--appblue);
    border-radius: 10px;
}

.dracula {
    background: linear-gradient(#cecece,#282828);
}

.contrast {
    background: linear-gradient(#f4fcf2,#3df400,#000200);
}

.basic {
    background: linear-gradient(#fffefe,#0036aa);
}

.retro {
    background: linear-gradient(#000000,#5a5a5a,#fc6a08);

}
</style>