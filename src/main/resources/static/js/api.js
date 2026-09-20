const API_URL="http://localhost:8080/api";
function token(){return localStorage.getItem("stockflow_token")}
function user(){try{return JSON.parse(localStorage.getItem("stockflow_user"))}catch{return null}}
function saveSession(d){localStorage.setItem("stockflow_token",d.token);localStorage.setItem("stockflow_user",JSON.stringify({id:d.id,username:d.username,role:d.role}));return user()}
function clearSession(){localStorage.removeItem("stockflow_token");localStorage.removeItem("stockflow_user")}
async function api(path,opt={}){const h={...(opt.headers||{})};if(token())h.Authorization="Bearer "+token();if(opt.body)h["Content-Type"]="application/json";const r=await fetch(API_URL+path,{...opt,headers:h});const t=await r.text();let d;try{d=t?JSON.parse(t):null}catch{d=t}if(r.status===401){clearSession();throw Error("Your session has expired. Please sign in again.")}if(r.status===403)throw Error("You do not have permission to perform this action.");if(!r.ok)throw Error(d?.message||d||"Request failed.");return d}
const money=v=>"₹"+Number(v||0).toLocaleString("en-IN",{minimumFractionDigits:2,maximumFractionDigits:2});
const esc=v=>{const d=document.createElement("div");d.textContent=v??"";return d.innerHTML}