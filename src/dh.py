#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright 2019 Alexander Kolbasov
#
# Тестирование DH


def getA(p, g, a):
    return (g**a) % p


def getK(p, a, B):
    return (B**a) % p


p, g = 1481, 3

a1 = 87
a2 = 51

A1 = getA(p, g, a1)
A2 = getA(p, g, a2)

print(A1)
print(A2)

print(getK(p, a1, A2))
print(getK(p, a2, A1))
