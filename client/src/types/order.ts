import type { Product } from "./product";

export interface Order {
    _id: string;
    user_id: string;
    products: Product[];
    price: number;
    status: string;
    received: string;
    expectedDelivery: string | null;
}
