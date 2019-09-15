<template>
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-body">
                        <slot name="body">
                            <label>
                                <input class="input-line-create-name" type="text" placeholder="Enter name folder"
                                       v-model="text"/>
                            </label>
                        </slot>
                    </div>

                    <div class="modal-footer">
                        <slot name="footer">
                            <div>
                                <button class="modal-cancel-button" @click="$emit('close')">
                                    отмена
                                </button>
                                <button v-if="isNew" class="modal-add-button" @click="save">
                                    добавить
                                </button>
                                <button v-if="!isNew" class="modal-add-button" @click="edit">
                                    изменить
                                </button>
                            </div>
                        </slot>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
    import api from '../plugins/backend-api.js'

    export default {
        props: {
            folder: Object,
            isNew: {
                value: false,
                required: true
            },
        },
        name: "modal",
        data() {
            return {
                text: this.isNew?'':this.folder.nameFolder,
                errors: []
            }
        },
        methods: {
            save() {
                api
                    .postFolder(this.folder.id, this.text)
                    .then(response => {
                        this.text = '';
                        this.$emit('close');
                        this.folder.child.push(response.data);
                    })
                    .catch(error => {
                        if (error.response) {
                            console.log(error.response.data);
                            console.log(error.response.status);
                            console.log(error.response.headers);
                        } else if (error.request) {
                            console.log(error.request);
                        } else {
                            this.errors.push(error);
                            console.log('Error', error.message);
                        }
                    })
            },
            edit() {
                api
                    .updateNameFolder(this.folder.id, this.text)
                    .then(response => {
                        this.$emit('close');
                        if (response.status === 200) {
                            this.folder.nameFolder = this.text
                        }
                        this.text = '';
                    })
                    .catch(error => {
                        if (error.response) {
                            console.log(error.response.data);
                            console.log(error.response.status);
                            console.log(error.response.headers);
                        } else if (error.request) {
                            console.log(error.request);
                        } else {
                            this.errors.push(error);
                            console.log('Error', error.message);
                        }
                    })
            },
        }
    }
</script>

<style scoped>
    .input-line-create-name {
        border: none;
        background: none;
        outline: none;
        border-bottom: 2px solid #42b983;
        font-size: 20px;
    }

    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .3s ease;
    }

    .modal-wrapper {
        display: table-cell;
        vertical-align: middle;
    }

    .modal-container {
        width: 300px;
        margin: 0px auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .3s ease;
        font-family: Helvetica, Arial, sans-serif;
    }

    .modal-header h3 {
        margin-top: 0;
        color: #42b983;
    }

    .modal-body {
        margin: 20px 0;
    }

    button {
        outline: none;
        border: none;
        color: white;
        text-transform: uppercase;
        border-radius: 10px;
        font-size: 16px;
        padding: 8px 0;
        display: inline-block;
        margin-left: 20px;
        margin-right: 20px;
    }

    .modal-cancel-button {
        background: #FA8072;
        width: 100px;
    }

    .modal-add-button {
        background: #42b983;
        width: 100px;
    }

    .modal-enter {
        opacity: 0;
    }

    .modal-leave-active {
        opacity: 0;
    }

    .modal-enter .modal-container,
    .modal-leave-active .modal-container {
        -webkit-transform: scale(1.1);
        transform: scale(1.1);
    }
</style>
