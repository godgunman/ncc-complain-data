//
//  OneComplainViewController.m
//  fakenews
//
//  Created by Golo on 14/6/6.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import "OneComplainViewController.h"

@interface OneComplainViewController ()

@end

@implementation OneComplainViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.channelNameLable.text = [self.selectedComplain valueForKey:@"channelName"];
    self.programNameLable.text = [self.selectedComplain valueForKey:@"programName"];
    self.cidLable.text = [NSString stringWithFormat:@"申訴案號 %@",[self.selectedComplain valueForKey:@"cid"]];
    NSString *complainDate = [self.selectedComplain valueForKey:@"date"];
    complainDate = [complainDate stringByReplacingOccurrencesOfString:@"-" withString:@"/"];
    complainDate = [[complainDate substringFromIndex:1] substringToIndex:9];
    self.complainDateLable.text = [NSString stringWithFormat:@"申訴日期 %@",complainDate];
    self.broadcastDateLable.text = [NSString stringWithFormat:@"播出日期 %@",[self.selectedComplain valueForKey:@"broadcastDate"]];
    self.broadcastTimeLable.text = [NSString stringWithFormat:@"播出時段 %@",[self.selectedComplain valueForKey:@"broadcastTime"]];
    self.complainTitleLable.text = [self.selectedComplain valueForKey:@"complainTitle"];
    self.complainContentLable.text = [self.selectedComplain valueForKey:@"complainContent"];
    self.replyContentLable.text = [self.selectedComplain valueForKey:@"replyContent"];
    
    CGFloat scrollViewHeight;
    CGFloat scrollViewWidth = self.oneComplainScrollView.contentSize.width;

    [self.complainTitleLable sizeToFit];
    scrollViewHeight = self.complainTitleLable.frame.origin.y + self.complainTitleLable.frame.size.height;
    scrollViewHeight = scrollViewHeight + 10;
    
    self.dividingLine1ImageView.frame = CGRectMake(0, scrollViewHeight, 280, 1);
    scrollViewHeight = scrollViewHeight + self.dividingLine1ImageView.frame.size.height + 10;
    
    self.complainContentIndicatorLable.frame = CGRectMake(
            self.complainContentIndicatorLable.frame.origin.x,
            scrollViewHeight,
            self.complainContentIndicatorLable.frame.size.width,
            self.complainContentIndicatorLable.frame.size.height);
    scrollViewHeight = scrollViewHeight + self.complainContentIndicatorLable.frame.size.height + 5;

    [self.complainContentLable sizeToFit];
    self.complainContentLable.frame = CGRectMake(
            self.complainContentLable.frame.origin.x,
            scrollViewHeight,
            self.complainContentLable.frame.size.width,
            self.complainContentLable.frame.size.height);
    scrollViewHeight = scrollViewHeight + self.complainContentLable.frame.size.height + 10;
    
    self.dividingLine2ImageView.frame = CGRectMake(0, scrollViewHeight, 280, 1);
    scrollViewHeight = scrollViewHeight + self.dividingLine2ImageView.frame.size.height + 10;

    self.replyContentIndicatorLable.frame = CGRectMake(
            self.replyContentIndicatorLable.frame.origin.x,
            scrollViewHeight,
            self.replyContentIndicatorLable.frame.size.width,
            self.replyContentIndicatorLable.frame.size.height);
    scrollViewHeight = scrollViewHeight + self.replyContentIndicatorLable.frame.size.height + 5;

    [self.replyContentLable sizeToFit];
    self.replyContentLable.frame = CGRectMake(
            self.replyContentLable.frame.origin.x,
            scrollViewHeight,
            self.replyContentLable.frame.size.width,
            self.replyContentLable.frame.size.height);
    scrollViewHeight = scrollViewHeight + self.replyContentLable.frame.size.height + 10;

    [self.oneComplainScrollView setContentSize:(CGSizeMake(scrollViewWidth,scrollViewHeight))];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)closeButtonClicked:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
