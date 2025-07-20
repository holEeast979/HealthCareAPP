export function setSessionStorage(keyStr, value) {
    sessionStorage.setItem(keyStr, JSON.stringify(value));
}

export function getSessionStorage(keyStr){
    var str = sessionStorage.getItem(keyStr);
    if (str == null || str == undefined || str == "" || str == 'null') {
        return null;
    }else{
        return JSON.parse(str);
    }
}
export function removeSessionStorage(keyStr){
    sessionStorage.removeItem(keyStr);
}