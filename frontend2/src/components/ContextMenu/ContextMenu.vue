<template>
    <div>
        <ul id="context-menu">
            <li class="context-menu-item" v-for="(option,index) in options" :key="index" @click="optionClicked(option)">{{option.name}}
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "ContextMenu",
        props: {
            options: {
                type: Array,
                required: true
            }
        },
        methods: {
            hello() {
                console.log('hello')
            },
            optionClicked(option) {
                this.$emit('option-clicked', {
                    option: option
                })
            }
        }
    }
</script>

<style scoped>
    #context-menu {
        top: 0;
        left: 0;
        margin: 0;
        padding: 0;
        display: none;
        list-style: none;
        position: absolute;
        z-index: 2147483647;
        border-bottom-width: 0px;
        background-color: #ECF0F1;
        box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.2);
        border-radius: 4px;
    }

    #context-menu.active {
        display: block;
    }

    .context-menu-item {
        color: #333;
        display: flex;
        cursor: pointer;
        padding: 5px 15px;
        align-items: center;
    }

    .context-menu-item:hover {
        background-color: #007AFF;
        color: white;
    }

    li {
        li :first-of-type {
            margin-top: 4px;
        }

        & :last-of-type {
            margin-bottom: 6px;
        }
    }
</style>
