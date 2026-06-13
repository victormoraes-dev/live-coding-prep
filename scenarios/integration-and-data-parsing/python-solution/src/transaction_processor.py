class Transaction:
    def __init__(self, id: str, amount: float, currency: str):

        if not id.startswith("TXN_"):
            raise ValueError(f"Invalid ID: {id}")

        amount_value = float(amount)
        if amount_value <= 0:
            raise ValueError(f"Amount must be positive: {amount}")

        if currency not in ("BRL", "USD", "EUR"):
            raise ValueError(f"unsupported currency: {currency}")

        self.id = id
        self.amount = amount_value
        self.currency = currency


def parse(transaction_line: str) -> Transaction | None:

    id, amount, currency = transaction_line.split(":")

    return Transaction(id=id, amount=amount, currency=currency)
