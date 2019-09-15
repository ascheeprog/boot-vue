import axios from 'axios'

const SERVER_URL = 'http://localhost:9000';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000
});

export default {
    getFolder: (id) => instance.get('/api/' + id),
    postFolder(id, nameFolder) {
        let newFolder = new URLSearchParams();
        newFolder.append('folderName', nameFolder);
        return instance.post('/api/'+id, newFolder)
    },
    deleteFolder(idFolder) {
        let deleteFolder = new URLSearchParams();
        deleteFolder.append('id', idFolder);
        return instance.delete('/api/delete', {data: deleteFolder});
    },
    updateNameFolder(idSelectFolder, newNameFolder) {
        let editName = new URLSearchParams();
        editName.append('idSelectFolder', idSelectFolder);
        editName.append('newNameFolder', newNameFolder);
        return instance.put('/api/edit',editName);
    },
    cutFolder(sourceId, targetId) {
        let cutOne = new URLSearchParams();
        cutOne.append('sourceId', sourceId);
        cutOne.append('targetId', targetId);
        return instance.put('/api/cut', cutOne);
    },
    copyFolder(sourceId, targetId) {
        let cutOne = new URLSearchParams();
        cutOne.append('sourceId', sourceId);
        cutOne.append('targetId', targetId);
        return instance.put('/api/copy', cutOne);
    },
}
