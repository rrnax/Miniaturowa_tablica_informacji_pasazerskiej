<template>
  <div class="account-confirmation">
    <div class="answer-box">
      <p>Potwierdzono weryfikacje użytkownika, możesz się zalogować</p>
    </div>
  </div>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
    name: "AccountConfirmationView",
    setup() {
        const authStore = useAuthStore();
        return { authStore };
    },
    mounted() {
        this.authStore.confirmAccount(this.$route.query.token);
        this.$emit("changePanel", "regis");
    },
}
</script>

<style scoped>
.account-confirmation{
  height: 290px;
}

.answer-box {
  width: 90%;
  height: 100px;
  background: aquamarine;
  margin: 20px auto;
  display: flex;
  border: 2px solid limegreen;
  border-radius: 20px;
  justify-content: center;
  align-items: center;
  font-size: 22px;
  font-family: 'Teko', sans-serif;
  color: green;
}

</style>