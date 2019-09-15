<template>
    <div>
        <ul class="folder-list">
            <router-link class="rout" :to="{name:'nameFolder', params:{id:1}}">Go to</router-link>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "home",
    }
</script>

<style scoped>
    ul.folder-list {
        margin: 0;
        padding: 10px;
        text-align: left;
        display: inline-block;
    }
    a {
        color: #42b983;
    }
</style>
