const DEFAULT_PRODUCT_IMAGES = {
    "WM-001": "/images/products/wireless-mouse.png",
    "IP17-001": "/images/products/iphone-17.png",
    "RM13P-001": "/images/products/redmi-13-pro.png",
    "KB-001": "/images/products/keyboard.png",
    "HP-001": "/images/products/headphones.png",
    "KB-007": "/images/products/mousepad.png",
    "IBS-202": "/images/products/laptop-stand.png"
};


function getProductImage(product) {

    // First priority: image saved in database
    if (product.imageUrl && product.imageUrl.trim()) {

        let image = product.imageUrl.trim();

        if (!image.startsWith("/")) {
            image = "/" + image;
        }

        return image;
    }

    // Second priority: default image based on SKU
    return DEFAULT_PRODUCT_IMAGES[product.sku] || null;
}


async function loadProducts() {

    const b = document.getElementById("products");

    if (!b) return;

    try {

        const p = await api("/products");

        if (!p?.length) {

            b.innerHTML =
                '<div class="empty">No products are currently listed.</div>';

            return;
        }

        b.innerHTML = p.map(x => {

            const s = Number(x.stock || 0);
            const image = getProductImage(x);

            return `
                <article class="product-card">

                    <div class="product-image">

                        ${
                            image
                                ? `
                                    <img
                                        src="${image}"
                                        alt="${esc(x.name)}"
                                        onerror="
                                            this.style.display='none';
                                            this.nextElementSibling.style.display='flex';
                                        "
                                    >

                                    <span
                                        class="icon image-fallback"
                                        style="display:none;">
                                        ${esc(
                                            (x.name || "P")[0]
                                                .toUpperCase()
                                        )}
                                    </span>
                                `
                                : `
                                    <span class="icon">
                                        ${esc(
                                            (x.name || "P")[0]
                                                .toUpperCase()
                                        )}
                                    </span>
                                `
                        }

                    </div>

                    <span class="status ${s ? "good" : "bad"}">
                        ${s ? "In stock" : "Out of stock"}
                    </span>

                    <h2>${esc(x.name)}</h2>

                    <code>${esc(x.sku)}</code>

                    <strong class="price">
                        ${money(x.price)}
                    </strong>

                    <p>
                        ${s} units available
                    </p>

                    <button
                        class="btn primary full"
                        ${s ? "" : "disabled"}
                        onclick="addToCart(${x.id})">
                        Add to cart
                    </button>

                </article>
            `;

        }).join("");

    } catch (e) {

        b.innerHTML =
            '<div class="empty">' +
            esc(e.message) +
            "</div>";
    }
}


async function addToCart(id) {

    const q = prompt("Quantity to add:", "1");

    if (q === null) return;

    const n = Number(q);

    if (!Number.isInteger(n) || n <= 0) {
        return alert("Enter a valid quantity.");
    }

    try {

        await api(
            `/cart/add?productId=${id}&quantity=${n}`,
            {
                method: "POST"
            }
        );

        location.href = "cart.html";

    } catch (e) {

        alert(e.message);
    }
}


document.addEventListener(
    "DOMContentLoaded",
    loadProducts
);