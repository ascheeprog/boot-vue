<template>
    <div id="folder" class="folder-content" @click="hideContextMenu()"
         @contextmenu.prevent="showContextMenu($event)">
        <div>
            <pulse-loader id="loader" :loading="loading" :color="color" :size="size"></pulse-loader>
        </div>
        <ul class="item">
            <li class="content" v-for="item in folder.child" :key="item.id" @click="item.active = !item.active"
                @contextmenu.prevent.stop="handleClick($event, item.id)"
                :class="{active:item.active}">
                <div @dblclick="dblClickMethod(item.id)">
                    <figure>
                        <img src="../assets/folder.png" width="100" height="100"
                             alt="folder">
                        <figcaption>{{item.nameFolder}}</figcaption>
                    </figure>
                </div>
            </li>
        </ul>
        <vue-simple-context-menu
                :elementId="'myUniqueId'"
                :options="options"
                :ref="'vueSimpleContextMenu'"
                @option-clicked="optionClicked">
        </vue-simple-context-menu>
        <div class="modal-left">
            <modal v-if="showModal" :folder="isNew?this.folder:this.selectFolder" :is-new="isNew"
                   @close="showModal = false">
            </modal>
        </div>
        <context-menu :options="options1" @option-clicked="optionContextClicked"></context-menu>
    </div>
</template>
<script>
    import Modal from "../components/Modal";
    import api from '../plugins/backend-api.js'
    import VueSimpleContextMenu from 'vue-simple-context-menu'
    import 'vue-simple-context-menu/dist/vue-simple-context-menu.css'
    import PulseLoader from 'vue-spinner/src/PulseLoader.vue'
    import ContextMenu from "../components/ContextMenu/ContextMenu";

    export default {
        name: 'folder',
        props: {id: 0},
        data() {
            return {
                showModal: false,
                isNew: {type: Boolean, value: true},
                selectFolder: Object,
                folder: [],
                errors: [],
                loading: false,
                color: "#2c3e50",
                size: "15px",
                cutOrCopy: Boolean,
                menuWidth: null,
                menuHeight: null,
            }
        },
        computed: {
            idCut() {
                return this.$store.getters.idCut;
            },
            options() {
                return this.$store.getters.options;
            },
            options1() {
                return this.$store.getters.options1;
            }
        },
        created() {
            this.fetchData();
        },
        watch: {
            '$route': 'fetchData',
        },
        methods: {
            fetchData() {
                let id = this.id;
                this.loading = true;
                if (id) {
                    setTimeout(() => {
                        api.getFolder(id).then(response => {
                                this.folder = response.data;
                                this.loading = false
                            }
                        ).catch(error => {
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
                    }, 2000);
                }
            },
            dblClickMethod(id) {
                this.$router.push({name: 'nameFolder', params: {id: id}})
            },
            handleClick(event, item) {
                this.$refs.vueSimpleContextMenu.showMenu(event, item)
            },
            optionClicked(event) {
                let slug = event.option.slug;
                if (slug === "delete") {
                    api.deleteFolder(event.item).then(response => {
                        if (response.status === 200) {
                            this.folder.child.splice((x => x.id === event.item), 1);
                        }
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
                } else if (slug === "cut" || slug === "copy") {
                    slug === "cut" ? this.cutOrCopy = true : this.cutOrCopy = false;
                    this.$store.dispatch('cut', event.item)
                } else if (slug === "rename") {
                    this.selectFolder = this.folder.child.find(x => x.id === event.item);
                    this.showModal = true;
                    this.isNew = false;
                }
            },
            optionContextClicked(event) {
                let slug = event.option.slug;
                if (slug === "add") {
                    this.showModal = true;
                    this.isNew = true
                } else if (slug === "paste") {
                    let value = this.cutOrCopy ? api.cutFolder(this.idCut, this.folder.id) : api.copyFolder(this.idCut, this.folder.id);
                    value.then(response => {
                        if (response.status === 200) {
                            this.folder.child.push(response.data);
                            console.log(response.data)
                        }
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
                        });
                    this.$store.dispatch('cut', this.cutOrCopy ? null : this.idCut);
                }
            },
            showContextMenu: function (event) {
                let menu = document.getElementById("context-menu");
                if (!menu) {
                    return
                }
                if (!this.menuWidth || !this.menuHeight) {
                    menu.style.visibility = "hidden";
                    menu.style.display = "block";
                    this.menuWidth = menu.offsetWidth;
                    this.menuHeight = menu.offsetHeight;
                    menu.removeAttribute("style");
                }

                if ((this.menuWidth + event.pageX) >= window.innerWidth) {
                    menu.style.left = (event.pageX - this.menuWidth) + "px";
                } else {
                    menu.style.left = event.pageX + "px";
                }

                if ((this.menuHeight + event.pageY) >= window.innerHeight) {
                    menu.style.top = (event.pageY - this.menuHeight) + "px";
                } else {
                    menu.style.top = event.pageY + "px";
                }
                menu.classList.add('active');
            },
            hideContextMenu() {
                document.getElementById("context-menu").classList.remove('active');
            }

        },
        components: {
            Modal: Modal,
            VueSimpleContextMenu: VueSimpleContextMenu,
            PulseLoader: PulseLoader,
            ContextMenu: ContextMenu
        }
    }
</script>

<style>
    html, body, #folder {
        margin: 0;
        width: 100%;
        height: 100%;
        display: flex;
        font-family: Roboto, Tahoma, sans-serif;
    }

    #loader {
        position: absolute;
        padding-bottom: 20px;
        display: block;
        right: 0;
        left: 0;
    }
    ul {
        position: absolute;
        font-size: 14px;
        text-align: left;
        color: black;
    }

    figcaption {
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
    }

    .content {
        width: 180px;
        display: inline-block;
    }

    a {
        color: #42b983;
    }

    .active {
        border-radius: 10px;
        background: #F2F2F2;
    }
</style>
