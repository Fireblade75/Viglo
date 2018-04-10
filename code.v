
class array {
    func Animal(): void => {
        echoAbc(2)
        echo '-'
        echoAbc(8)
    }

    func echoAbc(doc: int): void => {
        ? doc == 0 {
            echo 'a'
        } :? doc == 1 {
            echo 'b'
        } :? doc == 2 {
            echo 'c'
        } :? doc == 3 {
            echo 'd'
        } : {
            echo 'e'
        }
    }
}

