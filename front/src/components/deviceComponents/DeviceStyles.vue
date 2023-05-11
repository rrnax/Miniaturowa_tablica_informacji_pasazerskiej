<template>
    <div class="device-style">
        <p>Wybierz wyświetlany motyw interfejsu urządzenia</p>
        <form>
            <div class="input-set">
                <input class="check" type="checkbox" value="retro" id="retro"/>
                <label class="check-label" for="retro">Retro</label>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="basic" id="basic"/>
                <label class="check-label" for="basic">Standardowy</label>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="contrast" id="contrast"/>
                <label class="check-label" for="contrast">Kontrast</label>
            </div>
            <div class="input-set">
                <input class="check" type="checkbox" value="dracula" id="dracula"/>
                <label class="check-label" for="dracula">Dracula</label>
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

    methods: {
        async changeTheme(theme){
            await console.log(theme);
        }
    }   
}

</script>

<style>
.device-style {
    width: 100%;
    display: grid;
    justify-content: center;
}

.input-set {
    width: 100%;
    display: flex;
    justify-content: center;
}

.check {
    width: 25px;
    margin: 0;
    display: inline-block;
}

.check-label {
    display: inline-block;
}
</style>