async function loadOrders() {

    const b = document.getElementById("orders");

    if (!b) return;

    try {

        const orders = await api("/orders/my");

        if (!orders?.length) {

            b.innerHTML =
                '<tr><td colspan="3"><div class="empty">No orders yet.</div></td></tr>';

            return;
        }

        b.innerHTML = orders.map(order => `
            <tr>

                <td>
                    <b>${esc(order.productName)}</b>
                </td>

                <td>
                    ${money(order.price)}
                </td>

                <td>
                    ${order.quantity}
                </td>

            </tr>
        `).join("");

    } catch (e) {

        b.innerHTML =
            `<tr><td colspan="3">${esc(e.message)}</td></tr>`;
    }
}

document.addEventListener(
    "DOMContentLoaded",
    loadOrders
);