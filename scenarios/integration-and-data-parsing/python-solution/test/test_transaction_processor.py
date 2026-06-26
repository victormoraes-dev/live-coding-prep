import pytest
from transaction_processor import parse


def test_valid_brl_transaction():

    transaction = parse("TXN_001:100.00:BRL")
    assert transaction.amount == 100.00
    assert transaction.currency == "BRL"


def test_invalid_id_returns_none():
    transaction = parse("BBB_001:100.00:BRL")
    assert transaction is None

def test_malformed_line_returns_none():
    pass


def test_negative_amount_returns_none():
    pass
