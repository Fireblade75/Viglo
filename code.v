import 'string'

class Animal {
    func Animal(): void => {
        const t = play(3) + play(3)
        echo t == play(6)
        boolTest()
    }

    func play(roll: int): int => {
        return roll * 6
    }

    func boolTest(): void => {
        echo '-'
        let v = true
        let k = (not v) == false
        v = not k
        echo v
        echo k
    }
}


<~
struct Animal() {
    let bird = 20
    let cat = 5
    echo bird / cat                                 ~ 4
    echo bird - cat + (cat * cat)                   ~ 40

    let t = true and (false or true) or false
    echo t                                          ~ true
}
~>