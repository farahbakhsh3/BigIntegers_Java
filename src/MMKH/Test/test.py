import subprocess
import os


path = os.path.dirname(__file__)+"/Test.java"

def test(n1:int, n2:int):
    with subprocess.Popen(["java",f"{path}",f"{n1}",f"{n2}"],stdout=subprocess.PIPE, stdin=subprocess.PIPE) as p:
        stdout, _ = p.communicate()
        result = stdout.decode().strip().split("\r\n")
        
        add = int(result[0])
        sub = int(result[1])
        mul = int(result[2])
    print("Calculations for numbers:", n1,n2)
    print("Calculator addition answer:", n1+n2, "| java answer:", add, "| result:" ,n1+n2 == add)
    print("Calculator subtraction answer:", n1-n2, "| java answer:", sub, "| result:" ,n1-n2 == sub)
    print("Calculator multiplication answer:", n1*n2, "| java answer:", mul, "| result:" ,n1*n2 == mul)
    print("-"*100)
    
    
test(555, -333)
test(789, 0)
test(-123, 999)
test(876, -543)
test(111, 222)
test(-987, 654)
test(333, 111)
test(-555, 444)
test(789, 456)
test(-222, 111)
test(987, -654)
test(123456789, 987654321)
test(-987654321, 123456789)
test(111222333444, -555666777888)
test(-888777666555, 444333222111)
test(999888777, 666555444)
test(-111222333, 444555666)
test(12345678901234567890, -98765432109876543210)
test(-8765432109876543210, 9876543210987654321)
test(1111111111111111111, 2222222222222222222)
test(-3333333333333333333, -4444444444444444444)
test(123456789012345678901234567890, 987654321098765432109876543210)
test(-987654321098765432109876543210, 123456789012345678901234567890)
test(111222333444555666777888999, -555666777888999000111222333)
test(-888777666555444333222111000, 444333222111000555666777888)
test(999888777666555444333222111, 666555444333222111000999888)