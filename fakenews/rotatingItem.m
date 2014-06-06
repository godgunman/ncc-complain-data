//
//  rotatingItem.m
//  queen
//
//  Created by Golo on 13/10/1.
//  Copyright (c) 2013年 moskastudio. All rights reserved.
//

#import "rotatingItem.h"

@implementation rotatingItem

- (id)initWithFrame:(CGRect)frame
{
    CGRect screenBound = [[UIScreen mainScreen] bounds];
    CGSize screenSize = screenBound.size;
    if(screenSize.height==480)
    {
        frame = CGRectMake(frame.origin.x, frame.origin.y-44, frame.size.width, frame.size.height);
    }
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        
        
        self.image = [UIImage imageNamed:@"circle2"];
        [self createRotation];
        [self startRotating];

    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

- (void)createRotation
{
    CGFloat duration = 60*5;
    float repeat = 1;
    self.rotationAnimation = [CABasicAnimation animationWithKeyPath:@"transform.rotation.z"];
    self.rotationAnimation.toValue = [NSNumber numberWithFloat: M_PI * 2 * duration];
    self.rotationAnimation.duration = duration;
    self.rotationAnimation.cumulative = YES;
    self.rotationAnimation.repeatCount = repeat;
}

- (void)startRotating
{
    if([self.layer animationForKey:@"rotationAnimation"] == nil)
    {
        [self.layer addAnimation:self.rotationAnimation forKey:@"rotationAnimation"];
    }
    self.hidden = NO;
    
}

- (void)stopRotating
{
    self.hidden = YES;
    [self.layer removeAnimationForKey:@"rotationAnimation"];
}

@end
