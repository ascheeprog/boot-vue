export default {
    state: {
        idCut: null,
        options: [
            {
                name: 'Копировать',
                slug: 'copy'
            },{
                name: 'Вырезать',
                slug: 'cut'
            }, {
                name: 'Переименовать',
                slug: 'rename'
            },
            {
                name: 'Удалить',
                slug: 'delete'
            },
            ],
        options1:[
            {
                name: 'Добавить',
                slug: 'add',
            },
            {
                name: 'Вставить',
                slug: 'paste'
            }
        ]
    },
    mutations: {
        cut(state, id) {
            state.idCut = id;

        }
    },
    actions: {
        cut({commit}, payload) {
            commit('cut', payload)
        }
    },
    getters: {
        idCut(state) {
            return state.idCut;
        },
        options(state){
            return state.options;
        },
        options1(state){
            return state.options1;
        }
    }
}
