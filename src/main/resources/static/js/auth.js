async function login(username,password){const d=await api("/auth/login",{method:"POST",body:JSON.stringify({username,password})});if(!d?.token)throw Error("No JWT token was returned by the server.");saveSession(d);return user()}
async function register(username,password){await api("/auth/register",{method:"POST",body:JSON.stringify({username,password})})}
function logout(){clearSession();location.href="login.html"}
function requireAuth(role){
  const u=user();
  if(!token()||!u){location.href="login.html";return}
  if(role&&u.role!==role){location.href=u.role==="ADMIN"?"admin-dashboard.html":"catalog.html";return}
  const n=document.getElementById("nav");
  if(!n)return;
  const links=u.role==="ADMIN"
    ? '<a href="admin-dashboard.html">Dashboard</a><a href="admin-products.html">Products</a><a href="admin-inventory.html">Inventory</a>'
    : '<a href="catalog.html">Catalog</a><a href="cart.html">Cart</a><a href="orders.html">Orders</a>';
  n.innerHTML=links+'<div class="nav-user"><span class="pill">'+esc(u.username)+' · '+esc(u.role)+'</span><button class="btn small" onclick="logout()">Log out</button></div>';
}