export function dataURItoBlob(dataURI) {
  // convert base64/URLEncoded data component to raw binary data held in a string
  let byteString;
  if (dataURI.split(",")[0].indexOf("base64") >= 0) {
    byteString = atob(dataURI.split(",")[1]);
  } else {
    byteString = unescape(dataURI.split(",")[1]);
  }
  // separate out the mime component
  let mimeString = dataURI
    .split(",")[0]
    .split(":")[1]
    .split(";")[0];
  // writ,e the bytes of the string to a typed array
  let ia = new Uint8Array(byteString.length);
  for (let i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ia], { type: mimeString });
}
export function camel(str) {
  const camel = (str || "").replace(/-([^-])/g, g => g[1].toUpperCase());

  return capitalize(camel);
}

export function camelActual(str) {
  return (str || "").replace(/-(\w)/g, (_, c) => (c ? c.toUpperCase() : ""));
}

export function kebab(str) {
  return (str || "").replace(/([a-z])([A-Z])/g, "$1-$2").toLowerCase();
}

export function capitalize(str) {
  str = str || "";

  return `${str.substr(0, 1).toUpperCase()}${str.slice(1)}`;
}

export function getLongId(id) {
  // btoa() but for node
  return Buffer.from(`gid://shopify/Product/${id}`, "binary").toString(
    "base64"
  );
}

export function findProduct(store, id) {
  return store.state.store.products.find(p => p.id === id);
}

export function isOnSale(variants) {
  return variants.some(variant => {
    return parseFloat(variant.price) < parseFloat(variant.compareAtPrice);
  });
}

export function randomNumber(min, max) {
  return Math.floor(Math.random() * max) + min;
}

export function randomString(length = 5) {
  let text = "";
  const possible =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  for (let i = 0; i < length; i++) {
    text += possible.charAt(Math.floor(Math.random() * possible.length));
  }

  return text;
}

// Must be called in Vue context
export function goTo(id) {
  this.$vuetify.goTo(id).then(() => {
    if (!id) {
      return (document.location.hash = "");
    }

    if (history.replaceState) {
      history.replaceState(null, null, id);
    } else {
      document.location.hash = id;
    }
  });
}
