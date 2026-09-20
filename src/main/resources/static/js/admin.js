async function loadProducts() {
    const b = document.getElementById("products");

    if (!b) return;

    try {
        const p = await api("/products");

        b.innerHTML = p?.length
            ? p.map(x => `
                <tr>
                    <td>
                        <b>${esc(x.name)}</b>
                    </td>

                    <td>
                        <code>${esc(x.sku)}</code>
                    </td>

                    <td>
                        ${money(x.price)}
                    </td>

                    <td>
                        ${x.stock}
                    </td>

                    <td>
                        <button
                            class="btn danger small"
                            onclick="deleteProduct(${x.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `).join("")
            : '<tr><td colspan="5">No products.</td></tr>';

    } catch (e) {
        b.innerHTML =
            `<tr><td colspan="5">${esc(e.message)}</td></tr>`;
    }
}


async function deleteProduct(id) {
    if (!confirm("Delete this product?")) {
        return;
    }

    try {
        await api("/product/" + id, {
            method: "DELETE"
        });

        loadProducts();

    } catch (e) {
        alert(e.message);
    }
}


document.getElementById("productForm")?.addEventListener(
    "submit",
    async e => {

        e.preventDefault();

        const form = e.target;

        const skuInput = document.getElementById("sku");
        const nameInput = document.getElementById("name");
        const priceInput = document.getElementById("price");
        const stockInput = document.getElementById("stock");
        const imageInput = document.getElementById("imageUrl");

        try {

            await api("/product", {
                method: "POST",

                body: JSON.stringify({
                    sku: skuInput.value.trim(),
                    name: nameInput.value.trim(),
                    price: Number(priceInput.value),
                    stock: Number(stockInput.value),
                    imageUrl: imageInput.value.trim()
                })
            });

            form.reset();

            alert("Product added / stock updated successfully.");

            loadProducts();

        } catch (x) {
            alert(x.message);
        }
    }
);


async function loadInventory() {
    const b = document.getElementById("inventory");

    if (!b) return;

    try {

        const p = await api("/products");

        const totalProducts =
            document.getElementById("tp");

        const totalUnits =
            document.getElementById("tu");

        const lowStock =
            document.getElementById("ls");

        const outOfStock =
            document.getElementById("os");


        if (totalProducts) {
            totalProducts.textContent = p.length;
        }


        if (totalUnits) {
            totalUnits.textContent =
                p.reduce(
                    (s, x) => s + Number(x.stock || 0),
                    0
                );
        }


        if (lowStock) {
            lowStock.textContent =
                p.filter(
                    x => x.stock > 0 && x.stock <= 5
                ).length;
        }


        if (outOfStock) {
            outOfStock.textContent =
                p.filter(
                    x => x.stock <= 0
                ).length;
        }


        b.innerHTML = p.map(x => `
            <tr>

                <td>
                    <b>${esc(x.name)}</b>
                </td>

                <td>
                    <code>${esc(x.sku)}</code>
                </td>

                <td>
                    ${money(x.price)}
                </td>

                <td>
                    ${x.stock}
                </td>

                <td>

                    <span class="status ${
                        x.stock <= 0
                            ? "bad"
                            : x.stock <= 5
                                ? "warn"
                                : "good"
                    }">

                        ${
                            x.stock <= 0
                                ? "Out of stock"
                                : x.stock <= 5
                                    ? "Low stock"
                                    : "Healthy"
                        }

                    </span>

                </td>

            </tr>
        `).join("");

    } catch (e) {

        b.innerHTML =
            `<tr><td colspan="5">${esc(e.message)}</td></tr>`;
    }
}


document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
    loadInventory();
});